//
//  RecommendationDetailsWebViewController.m
//  OffersAppBuilder
//
//  Created by Vu Nguyen on 3/26/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import "RecommendationDetailsWebViewController.h"
#import "Constant.h"

@interface RecommendationDetailsWebViewController ()

@end

@implementation RecommendationDetailsWebViewController
@synthesize webView;

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.navigationItem.title = @"Recommend";
    
    NSLog(@"url = %@", self.strURL);
    // Do any additional setup after loading the view.
    NSURL *url = [NSURL URLWithString:self.strURL];
    NSURLRequest *requestObj = [NSURLRequest requestWithURL:url];
    [webView loadRequest:requestObj];
    
}

-(void)viewWillAppear:(BOOL)animated{
    //[self hideTabBar];
    }

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


@end